package com.example.round.gaia_18.Data;

import android.view.View;
import android.widget.TextView;

import com.example.round.gaia_18.Fragement.MenuSkill;

/**
 * Created by Round on 2017-09-10.
 */

public class SkillInfo {

    private int skillNo; //스킬 번호
    private String skillName; //스킬 이름
    private String skillImage; //스킬 이미지
    private String skillButtonImage; //스킬 버튼 이미지
    private int coolTime; //스킬 재사용 대기 시간
    private int passive; //지속 스킬 여부
    private int skillCase; //스킬 유현
    private int skillMaxLevel;

    // true : 사용중임
    // false : 사용하지 않고 있음.
    private Boolean skillUseState = false;
    // true : 데이터가 변함.
    // false : 데이터가 변하지 않음.
    private Boolean skillDataChange = true;
    // skillAdpater
    private View skillView = null;
    //SkillCoolTime Text;
    private TextView skillCoolTime = null;
    private TextView skillCoolTimeInApp = null;

    public SkillInfo(){}

    public SkillInfo(int skillNo, String skillName, String skillImage, String skillButtonImage, int coolTime, int passive, int skillCase) {
        this.skillNo = skillNo;
        this.skillName = skillName;
        this.skillImage = skillImage;
        this.skillButtonImage = skillButtonImage;
        this.coolTime = coolTime;
        this.passive = passive;
        this.skillCase = skillCase;
    }

    public int getSkillNo() {
        return skillNo;
    }

    public void setSkillNo(int skillNo) {
        this.skillNo = skillNo;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillImage() {
        return skillImage;
    }

    public void setSkillImage(String skillImage) {
        this.skillImage = skillImage;
    }

    public String getSkillButtonImage() {
        return skillButtonImage;
    }

    public void setSkillButtonImage(String skillButtonImage) {
        this.skillButtonImage = skillButtonImage;
    }

    public int getCoolTime() {
        return coolTime;
    }

    public void setCoolTime(int coolTime) {
        this.coolTime = coolTime;
    }

    public int getPassive() {
        return passive;
    }

    public void setPassive(int passive) {
        this.passive = passive;
    }

    public int getSkillCase() {
        return skillCase;
    }

    public void setSkillCase(int skillCase) {
        this.skillCase = skillCase;
    }

    public int getSkillMaxLevel() {
        return skillMaxLevel;
    }

    public void setSkillMaxLevel(int skillMaxLevel) {
        this.skillMaxLevel = skillMaxLevel;
    }

    public Boolean getSkillUseState() {
        return skillUseState;
    }

    public void setSkillUseState(Boolean skillUseState) {
        this.skillUseState = skillUseState;
    }

    public Boolean getSkillDataChange() {
        return skillDataChange;
    }

    public void setSkillDataChange(Boolean skillDataChange) {
        this.skillDataChange = skillDataChange;
    }

    public View getSkillView() {
        return skillView;
    }

    public void setSkillView(View skillViewHolder) {
        this.skillView = skillViewHolder;
    }

    public TextView getSkillCoolTime() {
        return skillCoolTime;
    }

    public void setSkillCoolTime(TextView skillCoolTime) {
        this.skillCoolTime = skillCoolTime;
    }

    public TextView getSkillCoolTimeInApp() {
        return skillCoolTimeInApp;
    }

    public void setSkillCoolTimeInApp(TextView skillCoolTimeInApp) {
        this.skillCoolTimeInApp = skillCoolTimeInApp;
    }
}
