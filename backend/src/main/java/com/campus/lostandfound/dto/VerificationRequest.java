package com.campus.lostandfound.dto;

public class VerificationRequest {
    private Integer matchScore;
    private Boolean livenessPassed;
    private String idFaceStatus;
    private String selfieFaceStatus;
    private String imageQualitySummary;

    public VerificationRequest() {
    }

    public Integer getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(Integer matchScore) {
        this.matchScore = matchScore;
    }

    public Boolean getLivenessPassed() {
        return livenessPassed;
    }

    public void setLivenessPassed(Boolean livenessPassed) {
        this.livenessPassed = livenessPassed;
    }

    public String getIdFaceStatus() {
        return idFaceStatus;
    }

    public void setIdFaceStatus(String idFaceStatus) {
        this.idFaceStatus = idFaceStatus;
    }

    public String getSelfieFaceStatus() {
        return selfieFaceStatus;
    }

    public void setSelfieFaceStatus(String selfieFaceStatus) {
        this.selfieFaceStatus = selfieFaceStatus;
    }

    public String getImageQualitySummary() {
        return imageQualitySummary;
    }

    public void setImageQualitySummary(String imageQualitySummary) {
        this.imageQualitySummary = imageQualitySummary;
    }
}
