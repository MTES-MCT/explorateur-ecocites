/*
 * Explorateur Écocités
 * Copyright (C) 2019 l'État, ministère chargé du logement
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.efficacity.explorateurecocites.beans.model;

import isotope.commons.entities.BaseEntity;
import java.lang.Boolean;
import java.lang.Long;
import java.lang.String;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Cette classe a été autogénérée. Elle sera écrasée à chaque génération.
 *
 * Date de génération : 23/01/2019
 */
@Entity
@Table(
    name = "exp_file_upload"
)
public class FileUpload extends BaseEntity {
  @Column(
      name = "name",
      nullable = false,
      length = 255
  )
  private String name;

  @Column(
      name = "type",
      nullable = false,
      length = 255
  )
  private String type;

  @Column(
      name = "id_object"
  )
  private Long idObject;

  @Column(
      name = "type_object",
      length = 255
  )
  private String typeObject;

  @Column(
      name = "original_name",
      length = 255
  )
  private String originalName;

  @Column(
      name = "title",
      length = 255
  )
  private String title;

  @Column(
      name = "legende",
      length = 2147483647
  )
  private String legende;

  @Column(
      name = "lieu",
      length = 2147483647
  )
  private String lieu;

  @Column(
      name = "description",
      length = 2147483647
  )
  private String description;

  @Column(
      name = "copyright",
      length = 2147483647
  )
  private String copyright;

  @Column(
      name = "autorisationpresse"
  )
  private Boolean autorisationpresse;

  @Column(
      name = "autorisationrevue"
  )
  private Boolean autorisationrevue;

  @Column(
      name = "autorisationexpo"
  )
  private Boolean autorisationexpo;

  @Column(
      name = "autorisationinternet"
  )
  private Boolean autorisationinternet;

  @Column(
      name = "autorisationsiteee"
  )
  private Boolean autorisationsiteee;

  @Column(
      name = "autorisationsupportmm"
  )
  private Boolean autorisationsupportmm;

  @Column(
      name = "numerisation"
  )
  private Long numerisation;

  @Column(
      name = "dateupload"
  )
  private LocalDateTime dateupload;

  public FileUpload() {
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Long getIdObject() {
    return this.idObject;
  }

  public void setIdObject(Long idObject) {
    this.idObject = idObject;
  }

  public String getTypeObject() {
    return this.typeObject;
  }

  public void setTypeObject(String typeObject) {
    this.typeObject = typeObject;
  }

  public String getOriginalName() {
    return this.originalName;
  }

  public void setOriginalName(String originalName) {
    this.originalName = originalName;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLegende() {
    return this.legende;
  }

  public void setLegende(String legende) {
    this.legende = legende;
  }

  public String getLieu() {
    return this.lieu;
  }

  public void setLieu(String lieu) {
    this.lieu = lieu;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCopyright() {
    return this.copyright;
  }

  public void setCopyright(String copyright) {
    this.copyright = copyright;
  }

  public Boolean getAutorisationpresse() {
    return this.autorisationpresse;
  }

  public void setAutorisationpresse(Boolean autorisationpresse) {
    this.autorisationpresse = autorisationpresse;
  }

  public Boolean getAutorisationrevue() {
    return this.autorisationrevue;
  }

  public void setAutorisationrevue(Boolean autorisationrevue) {
    this.autorisationrevue = autorisationrevue;
  }

  public Boolean getAutorisationexpo() {
    return this.autorisationexpo;
  }

  public void setAutorisationexpo(Boolean autorisationexpo) {
    this.autorisationexpo = autorisationexpo;
  }

  public Boolean getAutorisationinternet() {
    return this.autorisationinternet;
  }

  public void setAutorisationinternet(Boolean autorisationinternet) {
    this.autorisationinternet = autorisationinternet;
  }

  public Boolean getAutorisationsiteee() {
    return this.autorisationsiteee;
  }

  public void setAutorisationsiteee(Boolean autorisationsiteee) {
    this.autorisationsiteee = autorisationsiteee;
  }

  public Boolean getAutorisationsupportmm() {
    return this.autorisationsupportmm;
  }

  public void setAutorisationsupportmm(Boolean autorisationsupportmm) {
    this.autorisationsupportmm = autorisationsupportmm;
  }

  public Long getNumerisation() {
    return this.numerisation;
  }

  public void setNumerisation(Long numerisation) {
    this.numerisation = numerisation;
  }

  public LocalDateTime getDateupload() {
    return this.dateupload;
  }

  public void setDateupload(LocalDateTime dateupload) {
    this.dateupload = dateupload;
  }
}
