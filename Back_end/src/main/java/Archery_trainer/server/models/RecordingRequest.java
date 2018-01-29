package Archery_trainer.server.models;

public class RecordingRequest {
    private String archerEmail;
    private int shotId;
    private long timestamp;

    public RecordingRequest(String _archerEmail, int _shotId, long _timestamp) {
        archerEmail = _archerEmail;
        shotId = _shotId;
        timestamp = _timestamp;
    }

    public String getArcherEmail() {
        return archerEmail;
    }

    public void setArcherEmail(String archerEmail) {
        this.archerEmail = archerEmail;
    }

    public int getShotId() {
        return shotId;
    }

    public void setShotId(int shotId) {
        this.shotId = shotId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String toString() {
        return "Request for " + archerEmail + ", shot " + shotId + ", time " + timestamp;
    }
}
