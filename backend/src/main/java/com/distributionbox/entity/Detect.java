package com.distributionbox.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xjy
 * @since 2024-07-14
 */
@Getter
@Setter
  @TableName("sys_detect")
public class Detect implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pid", type = IdType.INPUT)
    private Integer pid;

    private String numberState;

    private String numberRemark;

    private String electronicState;

    private String electronicRemark;

    private String appearanceState;

    private String appearanceRemark;

    private String robustnessState;

    private String robustnessRemark;

    private String lockState;

    private String lockRemark;

    private String cableState;

    private String cableRemark;

    private String systemState;

    private String systemRemark;

    private String componentsState;

    private String componentsRemark;

    private String wiringState;

    private String wiringRemark;

    private String looseState;

    private String looseRemark;

    private String temperatureState;

    private String temperatureRemark;

    private String lightningState;

    private String lightningRemark;

    private String jumperState;

    private String jumperRemark;

    private String inoutState;

    private String inoutRemark;

    private String zeroState;

    private String zeroRemark;

    private String groundState;

    private String groundRemark;

    private String barrierState;

    private String barrierRemark;

    private String cleaningState;

    private String cleaningRemark;

    private String screwsState;

    private String screwsRemark;

    private String firstOtherState;

    private String firstOtherRemark;

    private String workingState;

    private String workingRemark;

    private String crackingState;

    private String crackingRemark;

    private String workingtemperatureState;

    private String workingtemperatureRemark;

    private String insulationState;

    private String insulationRemark;

    private String discolorationState;

    private String discolorationRemark;

    private String orderState;

    private String orderRemark;

    private String bridgeState;

    private String bridgeRemark;

    private String bridgeconnectState;

    private String bridgeconnectRemark;

    private String bridgelidState;

    private String bridgelidRemark;

    private String thirdState;

    private String thirdRemark;


}
