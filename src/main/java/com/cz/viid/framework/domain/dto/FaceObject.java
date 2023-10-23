package com.cz.viid.framework.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FaceObject {

    @JsonProperty("FaceID")
    private String FaceID;
    @JsonProperty("InfoKind")
    private Integer InfoKind;
    @JsonProperty("SourceID")
    private String SourceID;
    @JsonProperty("DeviceID")
    private String DeviceID;
    @JsonProperty("LeftTopX")
    private Integer LeftTopX;
    @JsonProperty("LeftTopY")
    private Integer LeftTopY;
    @JsonProperty("RightBtmX")
    private Integer RightBtmX;
    @JsonProperty("RightBtmY")
    private Integer RightBtmY;
    @JsonProperty("IDNumber")
    private String IDNumber;
    @JsonProperty("Name")
    private String Name;
    @JsonProperty("UsedName")
    private String UsedName;
    @JsonProperty("Alias")
    private String Alias;
    @JsonProperty("AgeUpLimit")
    private Integer AgeUpLimit;
    @JsonProperty("AgeLowerLimit")
    private Integer AgeLowerLimit;
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
    @JsonProperty("JobCategory")
    private String JobCategory;
    @JsonProperty("AccompanyNumber")
    private Integer AccompanyNumber;
    @JsonProperty("SkinColor")
    private String SkinColor;
    @JsonProperty("FaceStyle")
    private String FaceStyle;
    @JsonProperty("FacialFeature")
    private String FacialFeature;
    @JsonProperty("PhysicalFeature")
    private String PhysicalFeature;
    @JsonProperty("IsDriver")
    private Integer IsDriver;
    @JsonProperty("IsForeigner")
    private Integer IsForeigner;
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
    @JsonProperty("DetaineesSpecialIdentity")
    private String DetaineesSpecialIdentity;
    @JsonProperty("MemberTypeCode")
    private String MemberTypeCode;
    @JsonProperty("IsVictim")
    private String IsVictim;
    @JsonProperty("VictimType")
    private String VictimType;
    @JsonProperty("CorpseConditionCode")
    private String CorpseConditionCode;
    @JsonProperty("IsSuspiciousPerson")
    private String IsSuspiciousPerson;
    @JsonProperty("Attitude")
    private String Attitude;
    @JsonProperty("Similaritydegree")
    private String Similaritydegree;
    @JsonProperty("EyebrowStyle")
    private String EyebrowStyle;
    @JsonProperty("NoseStyle")
    private String NoseStyle;
    @JsonProperty("MustacheStyle")
    private String MustacheStyle;
    @JsonProperty("LipStyle")
    private String LipStyle;
    @JsonProperty("WrinklePouch")
    private String WrinklePouch;
    @JsonProperty("AcneStain")
    private String AcneStain;
    @JsonProperty("FreckleBirthmark")
    private String FreckleBirthmark;
    @JsonProperty("ScarDimple")
    private String ScarDimple;
    @JsonProperty("TabID")
    private String TabID;
    @JsonProperty("OtherFeature")
    private String OtherFeature;
    @JsonProperty("Maritalstatus")
    private String Maritalstatus;
    @JsonProperty("FamilyAddress")
    private String FamilyAddress;
    @JsonProperty("CollectorOrg")
    private String CollectorOrg;
    @JsonProperty("CollectorID")
    private String CollectorID;
    @JsonProperty("DeviceSNNo")
    private String DeviceSNNo;
    @JsonProperty("APSId")
    private String APSId;
    @JsonProperty("LocationMarkTime")
    private String LocationMarkTime;
    @JsonProperty("FaceAppearTime")
    private String FaceAppearTime;
    @JsonProperty("FaceDisAppearTime")
    private String FaceDisAppearTime;
    @JsonProperty("ShotTime")
    private String ShotTime;
    @JsonProperty("IDType")
    private String IDType;
    @JsonProperty("GenderCode")
    private String GenderCode;
    @JsonProperty("HairStyle")
    private String HairStyle;
    @JsonProperty("HairColor")
    private String HairColor;
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
    @JsonProperty("PassportType")
    private String PassportType;
    @JsonProperty("DetaineesIdentity")
    private String DetaineesIdentity;
    @JsonProperty("InjuredDegree")
    private String InjuredDegree;
    @JsonProperty("EntryTime")
    private String EntryTime;
    @ApiModelProperty("图片列表")
    @JsonProperty("SubImageList")
    private SubImageList SubImageList;

}
