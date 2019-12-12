package com.example.eaglevision;


import java.util.HashMap;
import java.util.Map;

public class DirectoryPersonController {
    public static class Person
    {
        /*
         * Person class represent the person or individual. Every person to be recognised or is recoginized is set here.
         * ID: Is the id of the person,
         * name: It is the name,
         * hairColor: Color of the hair,
         * CNIC: the CNIC number for the person of interest,
         * Skincolor: Color of the skin,
         * description: the description of the person described on entry,
         * suspectedcrime: the crime of the person,
         * lastKnownLocation: The last known location of the suspect,
         * viewholdercolor: Color of the status of the viewholder ; RED-> NOT FOUND  , GREEN-> FOUND , YELLOW -> INPROGRESS
         * idimageref: Reference of the image storage for the person of intrest's Photo.
         * imagename: Name of the image.
         * */
        private String ID;
        private String name;
        private String hairColor;
        private String CNIC;
        private String skinColor;
        private String description;
        private String suspectedCrime;
        private String lastKnownLocation;
        private int viewholderColor;
        private String idimageref;
        private String imagename;

        public Person()
        {
            // Default constructor required for calls to DataSnapshot.getValue(Post.class)
        }

        public Person(String ID, String name, String hairColor, String CNIC, String skinColor, String description,
                      String suspectedCrime, String lastKnownLocation, int viewholderColor,String idimgeref , String imagename)
        {
            this.ID= ID;
            this.name = name;
            this.hairColor = hairColor;
            this.CNIC = CNIC;
            this.skinColor = skinColor;
            this.description= description;
            this.suspectedCrime= suspectedCrime;
            this.lastKnownLocation=lastKnownLocation;
            this.viewholderColor= viewholderColor;
            this.idimageref=idimgeref;
            this.imagename=imagename;
        }


        public void setDetails(String ID, String name, String hairColor, String CNIC, String skinColor, String description,
                               String suspectedCrime, String lastKnownLocation, int viewholderColor ,String idimgeref , String imagename)
        {
            this.ID= ID;
            this.name = name;
            this.hairColor = hairColor;
            this.CNIC = CNIC;
            this.skinColor = skinColor;
            this.description= description;
            this.suspectedCrime= suspectedCrime;
            this.lastKnownLocation=lastKnownLocation;
            this.viewholderColor= viewholderColor;
            this.idimageref=idimgeref;
            this.imagename=imagename;

        }

        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("ID", ID);
            result.put("name", name);
            result.put("hairColor", hairColor);
            result.put("CNIC", CNIC);
            result.put("skinColor", skinColor);
            result.put("lastKnownLocation", lastKnownLocation);
            result.put("viewholderColor", viewholderColor);
            result.put("description", description);
            result.put("idimageref",idimageref);
            result.put("imagename",imagename);

            return result;
        }

        //Getters

        public int getViewholderColor() {
            return viewholderColor;
        }
        public String getSuspectedCrime() {
            return suspectedCrime;
        }
        public String getName() {
            return name;
        }
        public String getID() { return ID; }
        public String getCNIC() { return CNIC; }
        public String getImagename() { return imagename; }
        public String getIdimageref() { return idimageref; }
        public String getHairColor() {
            return hairColor;
        }
        public String getDescription() {
            return description;
        }
        public String getSkinColor() {
            return skinColor;
        }
        public String getLastKnownLocation() {
            return lastKnownLocation;
        }

        //Setters
        public void setID(String ID) {
            this.ID = ID;
        }
        public void setCNIC(String CNIC) {
            this.CNIC = CNIC;
        }
        public void setName(String name) {
            this.name = name;
        }
        public void setHairColor(String hairColor) {
            this.hairColor = hairColor;
        }
        public void setDescription(String description) { this.description = description; }
        public void setSkinColor(String skinColor) { this.skinColor = skinColor; }
        public void setIdimageref(String idimageref) { this.idimageref = idimageref; }
        public void setImagename(String imagename) { this.imagename = imagename; }
        public void setLastKnownLocation(String lastKnownLocation) { this.lastKnownLocation = lastKnownLocation; }
        public void setSuspectedCrime(String suspectedCrime) { this.suspectedCrime = suspectedCrime; }
        public void setViewholderColor(int viewholderColor) { this.viewholderColor = viewholderColor; }
    }



}
