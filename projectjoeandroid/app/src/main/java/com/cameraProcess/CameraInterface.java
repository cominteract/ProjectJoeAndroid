package com.cameraProcess;

/**
 * Created by andreinsigne on 22/11/2017.
 */

public interface CameraInterface {
    public void cameraPictureTakenSuccess();
    public void cameraPictureCancelled();
    public void cameraPictureFailed();
    public void videoRecordTakenSuccess();
    public void videoRecordCancelled();
    public void videoRecordFailed();
}
