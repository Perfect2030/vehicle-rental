package com.htt.vehiclerental.bll;

import java.util.List;

import com.htt.vehiclerental.dal.ExtraFeeTypeDAL;
import com.htt.vehiclerental.dto.ExtraFeeType;

public class ExtraFeeTypeBLL {

    public static final int SUCCESS = 1;
    public static final int NOT_FOUND = -1;
    public static final int DATABASE_ERROR = -3;
    public static final int INVALID_INPUT = -4;
    public static final int FEE_EXISTS = -5;
    
    public static int addExtraFeeType(ExtraFeeType feeType) {
        if (feeType.getName() == null || feeType.getName().isEmpty() ||
            feeType.getAmount() < 0) {
            return INVALID_INPUT;
        }

        if (ExtraFeeTypeDAL.getExtraFeeTypeByName(feeType.getName()) != null) {
            return FEE_EXISTS; // Extra fee type with this name already exists
        }

        if (!ExtraFeeTypeDAL.add(feeType)) {
            return DATABASE_ERROR; // Failed to add extra fee type to the database
        }

        return SUCCESS;
    }

    public static int updateExtraFeeType(ExtraFeeType feeType) {
        if (feeType.getName() == null || feeType.getName().isEmpty() ||
            feeType.getAmount() < 0) {
            return INVALID_INPUT;
        }

        if (ExtraFeeTypeDAL.getExtraFeeType(feeType.getId()) == null) {
            return NOT_FOUND; // Extra fee type with this ID does not exist
        }

        if (!ExtraFeeTypeDAL.update(feeType)) {
            return DATABASE_ERROR; // Failed to update extra fee type in the database
        }

        return SUCCESS;
    }

    public static int deleteExtraFeeType(int id) {
        if (id <= 0) {
            return INVALID_INPUT;
        }

        if (ExtraFeeTypeDAL.getExtraFeeType(id) == null) {
            return NOT_FOUND; // Extra fee type with this ID does not exist
        }

        if (!ExtraFeeTypeDAL.delete(id)) {
            return DATABASE_ERROR; // Failed to delete extra fee type from the database
        }

        return SUCCESS;
    }

    public static ExtraFeeType getExtraFeeType(int id) {
        if (id <= 0) {
            return null;
        }

        return ExtraFeeTypeDAL.getExtraFeeType(id);
    }

    public static List<ExtraFeeType> searchExtraFeeTypes(String keyword, int sortOption) {
        if (keyword == null) {
             keyword = "";
         }
 
         return ExtraFeeTypeDAL.searchExtraFeeTypes(keyword.trim(), sortOption);
    }

}
