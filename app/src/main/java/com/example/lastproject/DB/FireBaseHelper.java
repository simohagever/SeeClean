package com.example.lastproject.DB;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

public class FireBaseHelper {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage fireStorage = FirebaseStorage.getInstance();

    Context context;

    public FireBaseHelper(Context context) {
        this.context = context;
    }

    /**
     * Adds a new user to the Firestore database.
     *
     * @param id        User ID
     * @param name      User first name
     * @param LastName  User last name
     * @param password  User password
     * @param email     User email
     * @param addtess   User address
     */
    public void AddUser(String id, String name, String LastName, String password, String email, String addtess) {
        Map<String, Object> user = new HashMap<>();
        user.put("first", name);
        user.put("last", LastName);
        user.put("password", password);
        user.put("email", email);
        user.put("address", addtess);

        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // User added successfully
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to add user
                    }
                });
    }

    public interface markerAdded {
        void onMarkerAdded();
    }

    /**
     * Adds a new marker to the Firestore database and uploads the associated image to Firebase Storage.
     *
     * @param Description Description of the marker
     * @param time        Time of the marker event
     * @param date        Date of the marker event
     * @param picture     Bitmap image associated with the marker
     * @param latLng      Latitude and longitude of the marker
     * @param callback    Callback interface for marker addition completion
     */
    public void AddMarker(String Description, String time, String date, Bitmap picture, LatLng latLng, markerAdded callback) {
        StorageReference storageRef = fireStorage.getReference();
        StorageReference markerImagesRef = storageRef.child("images/" + UUID.randomUUID());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        markerImagesRef.putBytes(data).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    Map<String, Object> marker = new HashMap<>();
                    marker.put("Description", Description);
                    marker.put("Time", time);
                    marker.put("Date", date);
                    marker.put("Latitude", latLng.latitude);
                    marker.put("Longitude", latLng.longitude);
                    marker.put("PicPath", markerImagesRef.getPath());

                    db.collection("markers").add(marker).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            callback.onMarkerAdded();
                        }
                    });
                } else {
                    callback.onMarkerAdded();
                }
            }
        });
    }

    public interface markerCreated {
        void onMarkersCreated(LinkedList<Marker> list);
    }

    /**
     * Places all markers from the Firestore database onto a GoogleMap.
     *
     * @param googleMap GoogleMap object where markers will be placed
     * @param callback  Callback interface for marker creation completion
     */
    public void PlaceMarkers(GoogleMap googleMap, markerCreated callback) {
        db.collection("markers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    LinkedList<Marker> list = new LinkedList<>();
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        LatLng latLng = new LatLng(Double.parseDouble(documentSnapshot.getData().get("Latitude").toString()), Double.parseDouble(documentSnapshot.getData().get("Longitude").toString()));
                        Marker marker = googleMap.addMarker(new MarkerOptions().title("simo").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).position(latLng));
                        marker.setTag(documentSnapshot.getId());
                        list.add(marker);
                    }
                    callback.onMarkersCreated(list);
                }
            }
        });
    }

    public interface MarkerFetched {
        void onMarkerFetched(String Description, String Time, String Date, Uri photo);
    }

    /**
     * Fetches marker information from Firestore and retrieves the associated image from Firebase Storage.
     *
     * @param id       Marker ID
     * @param callback Callback interface for fetching marker information
     */
    public void getMarkerInfo(String id, MarkerFetched callback) {
        db.collection("markers").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                StorageReference imageRef = fireStorage.getReferenceFromUrl("gs://lastproject-a62b0.appspot.com/" + documentSnapshot.getData().get("PicPath").toString());
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        callback.onMarkerFetched(documentSnapshot.getData().get("Description").toString(), documentSnapshot.getData().get("Time").toString(), documentSnapshot.getData().get("Date").toString(), uri);
                    }
                });
            }
        });
    }

    public interface DeletedMarker {
        void onDelete();
    }

    /**
     * Deletes a marker from Firestore and its associated image from Firebase Storage.
     *
     * @param id       Marker ID
     * @param callback Callback interface for marker deletion completion
     */
    public void deleteMarker(String id, DeletedMarker callback) {
        db.collection("markers").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    String picPath = task.getResult().getData().get("PicPath").toString();
                    StorageReference markerImagesRef = fireStorage.getReference().child(picPath);
                    markerImagesRef.delete();

                    db.collection("markers").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            callback.onDelete();
                        }
                    });
                }
            }
        });
    }

    /**
     * Deletes a marker from the Firestore database by its ID.
     *
     * This method searches for a marker document in the "markers" collection with a PictureKey
     * matching the specified marker ID. If found, the associated image file is deleted from
     * Firebase Storage, and then the marker document is deleted from Firestore.
     *
     * @param marker The ID of the marker to be deleted.
     */
    public void deleteMarkerByID(String marker) {
        db.collection("markers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    boolean didFind = false;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getData().get("PicPath").toString().equals("/images/"+marker)) {
                            didFind = true;
                            final String documentID = document.getId();
                            db.collection("markers").document(documentID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    String picPath = documentSnapshot.getData().get("PicPath").toString();
                                    StorageReference ref = null;
                                    if (picPath != null && !picPath.equals("")) {
                                        ref = fireStorage.getReference().child(picPath);
                                    }

                                    // Delete the file
                                    if (ref != null) {
                                        ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // File deleted successfully
                                                db.collection("markers").document(documentID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                                // Uh-oh, an error occurred!
                                            }
                                        });
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Failed to retrieve picture", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    if (!didFind) {
                        Toast.makeText(context, "Report does not exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Report does not exist", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Deletes all markers from the Firestore database.
     *
     * This method retrieves all documents in the "markers" collection and deletes each one along with
     * its associated image file in Firebase Storage. A toast message is shown indicating that all markers
     * have been deleted.
     */
    public void deleteAllMarkers() {
        db.collection("markers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        fireStorage.getReference().child(document.getData().get("PicPath").toString()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                db.collection("markers").document(document.getId()).delete();
                            }
                        });
                    }
                    Toast.makeText(context, "Deleted All Markers", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
