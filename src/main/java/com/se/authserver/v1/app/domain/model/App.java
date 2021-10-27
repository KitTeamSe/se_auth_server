package com.se.authserver.v1.app.domain.model;

import com.se.authserver.v1.app_callback_url.domain.model.CallbackUrl;
import com.se.authserver.v1.common.domain.model.BaseEntity;
import com.se.authserver.v1.resource_metadata_app_mapping.domain.model.ResourceMetadataAppMapping;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class App extends BaseEntity {

  public App(String name
      , String clientId
      , String clientSecret
      , DevelopProgress developProgress) {
    this.name = name;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.developProgress = developProgress;
  }

  public void update(String name, DevelopProgress developProgress
  ) {
    this.name = name;
    this.developProgress = developProgress;
  }

  @Id
  @Column(name = "app_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long appId;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String clientId;

  @Column(nullable = false)
  private String clientSecret;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private DevelopProgress developProgress;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "app_id")
  private List<ResourceMetadataAppMapping> resourceMetadataAppMappings;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "app_id")
  private List<CallbackUrl> callbackUrls;

  public void setResourceMetadataAppMappings(
      List<ResourceMetadataAppMapping> resourceMetadataAppMappings) {
    if (this.resourceMetadataAppMappings == null) {
      this.resourceMetadataAppMappings = resourceMetadataAppMappings;
    } else {
      this.resourceMetadataAppMappings.clear();
      this.resourceMetadataAppMappings.addAll(resourceMetadataAppMappings);
    }
  }

  public void setCallbackUrls(List<CallbackUrl> callbackUrls) {
    if (this.callbackUrls == null) {
      this.callbackUrls = callbackUrls;
    } else {
      this.callbackUrls.clear();
      this.callbackUrls.addAll(callbackUrls);
    }
  }
}
