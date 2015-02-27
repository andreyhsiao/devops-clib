package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.Exception;

import java.util.Date;

import com.collabnet.ce.soap60.webservices.frs.ReleaseSoapDO;

public final class ReleaseData {
  ReleaseData(final ReleaseSoapDO releaseSoapDO) throws Exception {
    if (releaseSoapDO == null) {
      throw new Exception("argument 'releaseSoapDO' is null");
    }

    this.releaseSoapDO = releaseSoapDO;
  }

  public String getCreatedBy() {
    return releaseSoapDO.getCreatedBy();
  }

  public Date getCreatedDate() {
    return releaseSoapDO.getCreatedDate();
  }

  public String getDescription() {
    return releaseSoapDO.getDescription();
  }

  public int getDownloaded() {
    return releaseSoapDO.getDownloaded();
  }

  public String getId() {
    return releaseSoapDO.getId();
  }

  public String getLastModifiedBy() {
    return releaseSoapDO.getLastModifiedBy();
  }

  public Date getLastModifiedDate() {
    return releaseSoapDO.getLastModifiedDate();
  }

  public String getMaturity() {
    return releaseSoapDO.getMaturity();
  }

  public String getParentFolderId() {
    return releaseSoapDO.getParentFolderId();
  }

  public String getPath() {
    return releaseSoapDO.getPath();
  }

  public String getProjectId() {
    return releaseSoapDO.getProjectId();
  }

  public String getStatus() {
    return releaseSoapDO.getStatus();
  }

  public String getTitle() {
    return releaseSoapDO.getTitle();
  }

  public int getVersion() {
    return releaseSoapDO.getVersion();
  }

  private final ReleaseSoapDO releaseSoapDO;
}