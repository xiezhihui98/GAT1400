package com.cz.viid.framework.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Person {

    @JsonProperty("PersonID")
    private String PersonID;
    @JsonProperty("InfoKind")
    private String InfoKind;
    @JsonProperty("SourceID")
    private String SourceID;
    @JsonProperty("DeviceID")
    private String DeviceID;
    @JsonProperty("LeftTopY")
    private String LeftTopY;
    @JsonProperty("RightBtmX")
    private String RightBtmX;
    @JsonProperty("RightBtmY")
    private String RightBtmY;
    @JsonProperty("LocationMarkTime")
    private String LocationMarkTime;
    @JsonProperty("PersonAppearTime")
    private String PersonAppearTime;
    @JsonProperty("PersonDisAppearTime")
    private String PersonDisAppearTime;
    @JsonProperty("IDType")
    private String IDType;
    @JsonProperty("IDNumber")
    private String IDNumber;
    @JsonProperty("Name")
    private String Name;
    @JsonProperty("UsedName")
    private String UsedName;
    @JsonProperty("Alias")
    private String Alias;
    @JsonProperty("GenderCode")
    private String GenderCode;
    @JsonProperty("AgeUpLimit")
    private String AgeUpLimit;
    @JsonProperty("AgeLowerLimit")
    private String AgeLowerLimit;
    @JsonProperty("EthicCode")
    private String EthicCode;
    @JsonProperty("NationalityCode")
    private String NationalityCode;
    @JsonProperty("NativeCityCode")
    private String NativeCityCode;
    @JsonProperty("ResidenceAdminDivision")
    private String ResidenceAdminDivision;
    @JsonProperty("ChineseAccentCode")
    private String ChineseAccentCode;
    @JsonProperty("PersonOrg")
    private String PersonOrg;
    @JsonProperty("JobCategory")
    private String JobCategory;
    @JsonProperty("AccompanyNumber")
    private String AccompanyNumber;
    @JsonProperty("HeightUpLimit")
    private String HeightUpLimit;
    @JsonProperty("HeightLowerLimit")
    private String HeightLowerLimit;
    @JsonProperty("BodyType")
    private String BodyType;
    @JsonProperty("SkinColor")
    private String SkinColor;
    @JsonProperty("HairStyle")
    private String HairStyle;
    @JsonProperty("HairColor")
    private String HairColor;
    @JsonProperty("Gesture")
    private String Gesture;
    @JsonProperty("Status")
    private String Status;
    @JsonProperty("FaceStyle")
    private String FaceStyle;
    @JsonProperty("FacialFeature")
    private String FacialFeature;
    @JsonProperty("PhysicalFeature")
    private String PhysicalFeature;
    @JsonProperty("BodyFeature")
    private String BodyFeature;
    @JsonProperty("HabitualMovement")
    private String HabitualMovement;
    @JsonProperty("Behavior")
    private String Behavior;
    @JsonProperty("BehaviorDescription")
    private String BehaviorDescription;
    @JsonProperty("Appendant")
    private String Appendant;
    @JsonProperty("AppendantDescription")
    private String AppendantDescription;
    @JsonProperty("UmbrellaColor")
    private String UmbrellaColor;
    @JsonProperty("RespiratorColor")
    private String RespiratorColor;
    @JsonProperty("CapStyle")
    private String CapStyle;
    @JsonProperty("CapColor")
    private String CapColor;
    @JsonProperty("GlassStyle")
    private String GlassStyle;
    @JsonProperty("GlassColor")
    private String GlassColor;
    @JsonProperty("ScarfColor")
    private String ScarfColor;
    @JsonProperty("BagStyle")
    private String BagStyle;
    @JsonProperty("BagColor")
    private String BagColor;
    @JsonProperty("CoatStyle")
    private String CoatStyle;
    @JsonProperty("CoatLength")
    private String CoatLength;
    @JsonProperty("CoatColor")
    private String CoatColor;
    @JsonProperty("TrousersStyle")
    private String TrousersStyle;
    @JsonProperty("TrousersColor")
    private String TrousersColor;
    @JsonProperty("TrousersLen")
    private String TrousersLen;
    @JsonProperty("ShoesStyle")
    private String ShoesStyle;
    @JsonProperty("ShoesColor")
    private String ShoesColor;
    @JsonProperty("IsDriver")
    private Integer IsDriver;
    @JsonProperty("IsForeigner")
    private Integer IsForeigner;
    @JsonProperty("PassportType")
    private String PassportType;
    @JsonProperty("ImmigrantTypeCode")
    private String ImmigrantTypeCode;
    @JsonProperty("IsSuspectedTerrorist")
    private Integer IsSuspectedTerrorist;
    @JsonProperty("SuspectedTerroristNumber")
    private String SuspectedTerroristNumber;
    @JsonProperty("IsCriminalInvolved")
    private Integer IsCriminalInvolved;
    @JsonProperty("CriminalInvolvedSpecilisationCode")
    private String CriminalInvolvedSpecilisationCode;
    @JsonProperty("BodySpeciallMark")
    private String BodySpeciallMark;
    @JsonProperty("CrimeMethod")
    private String CrimeMethod;
    @JsonProperty("CrimeCharacterCode")
    private String CrimeCharacterCode;
    @JsonProperty("EscapedCriminalNumber")
    private String EscapedCriminalNumber;
    @JsonProperty("IsDetainees")
    private Integer IsDetainees;
    @JsonProperty("DetentionHouseCode")
    private String DetentionHouseCode;
    @JsonProperty("DetaineesIdentity")
    private String DetaineesIdentity;
    @JsonProperty("DetaineesSpecialIdentity")
    private String DetaineesSpecialIdentity;
    @JsonProperty("MemberTypeCode")
    private String MemberTypeCode;
    @JsonProperty("IsVictim")
    private Integer IsVictim;
    @JsonProperty("VictimType")
    private String VictimType;
    @JsonProperty("InjuredDegree")
    private String InjuredDegree;
    @JsonProperty("CorpseConditionCode")
    private String CorpseConditionCode;
    @JsonProperty("IsSuspiciousPerson")
    private Integer IsSuspiciousPerson;
    @JsonProperty("SubImageList")
    private SubImageList subImageList;
}
