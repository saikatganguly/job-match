package org.swipejob.test.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.TextScore;

import java.util.Date;
import java.util.List;

@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class Job {
    @Id
    private Integer jobId;
    private Boolean driverLicenseRequired;
    private List<String> requiredCertificates;
    private Location location;
    private String billRate;
    private Integer workersRequired;
    private Date startDate;
    private String about;
    private String jobTitle;
    private String company;
    private String guid;
    @TextScore
    private float score;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Boolean getDriverLicenseRequired() {
        return driverLicenseRequired;
    }

    public void setDriverLicenseRequired(Boolean driverLicenseRequired) {
        this.driverLicenseRequired = driverLicenseRequired;
    }

    public List<String> getRequiredCertificates() {
        return requiredCertificates;
    }

    public void setRequiredCertificates(List<String> requiredCertificates) {
        this.requiredCertificates = requiredCertificates;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getBillRate() {
        return billRate;
    }

    public void setBillRate(String billRate) {
        this.billRate = billRate;
    }

    public Integer getWorkersRequired() {
        return workersRequired;
    }

    public void setWorkersRequired(Integer workersRequired) {
        this.workersRequired = workersRequired;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public class Location{
        private double longitude;
        private double latitude;

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }
}
