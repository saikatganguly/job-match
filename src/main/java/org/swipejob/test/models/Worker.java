package org.swipejob.test.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document
public class Worker {

    @Id
    private Integer userId;
    private String guid;
    private Integer age;
    private Integer rating;
    private Boolean isActive;
    private List<String> certificates;
    private List<String> skills;
    private JobSearchAddress jobSearchAddress;
    private String transportation;
    private Boolean hasDriversLicense;
    private String phone;
    private String email;
    private Map<String , String> name;
    private List<Map<String,String>> availability;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public List<String> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<String> certificates) {
        this.certificates = certificates;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public JobSearchAddress getJobSearchAddress() {
        return jobSearchAddress;
    }

    public void setJobSearchAddress(JobSearchAddress jobSearchAddress) {
        this.jobSearchAddress = jobSearchAddress;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public Boolean getHasDriversLicense() {
        return hasDriversLicense;
    }

    public void setHasDriversLicense(Boolean hasDriversLicense) {
        this.hasDriversLicense = hasDriversLicense;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, String> getName() {
        return name;
    }

    public void setName(Map<String, String> name) {
        this.name = name;
    }

    public List<Map<String, String>> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Map<String, String>> availability) {
        this.availability = availability;
    }

    public class JobSearchAddress{
        private String unit;
        private Integer maxJobDistance;
        private double longitude;
        private double latitude;

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public Integer getMaxJobDistance() {
            return maxJobDistance;
        }

        public void setMaxJobDistance(Integer maxJobDistance) {
            this.maxJobDistance = maxJobDistance;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
    }

    @Override
    public String toString() {
        return "Worker{" +
                "userId=" + userId +
                ", guid='" + guid + '\'' +
                ", age=" + age +
                ", rating=" + rating +
                ", isActive=" + isActive +
                ", certificates=" + certificates +
                ", skills=" + skills +
                ", jobSearchAddress=" + jobSearchAddress +
                ", transportation='" + transportation + '\'' +
                ", hasDriversLicense=" + hasDriversLicense +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", name=" + name +
                ", availability=" + availability +
                '}';
    }
}
