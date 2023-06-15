package com.rizahanmiy.newsapp.data.local

import android.content.SharedPreferences
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_ACCESS_TOKEN
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_CART
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_CART_NULL
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_EMAIL
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_HARVEST_DATE
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_ID_USER
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_ISCHECKOUT
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_IS_COACH_MARK_CASHBACK_SOCIAL_BUYING
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_IS_COACH_MARK_COUPON_DETAIL
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_IS_COACH_MARK_FIRST_COUPON
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_IS_COACH_MARK_RATING_DETAIL
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_IS_COACH_MARK_RATING_HOME
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_IS_COACH_MARK_SOCIAL_BUYING
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_IS_COACH_MARK_SOCIAL_BUYING_BUTTON
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_IS_LOGIN
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_IS_ONBOARDING
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_IS_ONBOARDING_QUANTITY
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_IS_ONBOARDING_SOCIAL_BUYING
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_IS_WELCOME_SOCIAL_BUYING
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_NAME_USER
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_NOTIF
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_REGISTERED
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_SEARCH
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_TOKEN_FIREBASE
import com.rizahanmiy.newsapp.utils.constants.AppConstants.KeyPreferences.KEY_UNREAD


class LocalPreferences(private val sharedPreferences: SharedPreferences) {

    fun storeAccessToken(accessToken: String) {
        sharedPreferences.edit().putString(KEY_ACCESS_TOKEN, accessToken).apply()
    }

    fun storeTokenFirebase(firebase: String) {
        sharedPreferences.edit().putString(KEY_TOKEN_FIREBASE, firebase).apply()
    }

    fun storeEmail(email: String) {
        sharedPreferences.edit().putString(KEY_EMAIL, email).apply()
    }

    fun storeName(name: String) {
        sharedPreferences.edit().putString(KEY_NAME_USER, name).apply()
    }

    fun storeIdUser(id: String) {
        sharedPreferences.edit().putString(KEY_ID_USER, id).apply()
    }

    fun storeCountCart(count: Int) {
        sharedPreferences.edit().putInt(KEY_CART, count).apply()
    }

    fun storeHarvestDate(harvestDate: String) {
        sharedPreferences.edit().putString(KEY_HARVEST_DATE, harvestDate).apply()
    }

    fun storeSearch(search: String) {
        sharedPreferences.edit().putString(KEY_SEARCH, search).apply()
    }

    fun storeCategory(hashMap: HashMap<String, String>){
        for ((key, value) in hashMap.entries){
            sharedPreferences.edit().putString(key, value).apply()
        }
    }

    fun storeUnread(unread: String) {
        sharedPreferences.edit().putString(KEY_UNREAD, unread).apply()
    }

    fun isCartEmpty(isCartEmpty : Boolean){
        sharedPreferences.edit().putBoolean(KEY_CART_NULL, isCartEmpty).apply()
    }

    fun getIsCartEmpty():Boolean{
        return sharedPreferences.getBoolean(KEY_CART_NULL, false)
    }

    fun isRegistered(isRegistered : Boolean){
        sharedPreferences.edit().putBoolean(KEY_REGISTERED, isRegistered).apply()
    }

    fun isCheckout(isCheckout : Boolean){
        sharedPreferences.edit().putBoolean(KEY_ISCHECKOUT, isCheckout).apply()
    }

    fun getIsRegistered():Boolean{
        return sharedPreferences.getBoolean(KEY_REGISTERED, false)
    }

    fun getIsCheckout():Boolean{
        return sharedPreferences.getBoolean(KEY_ISCHECKOUT, false)
    }

    fun getHashMapCategory(keyContains: String): Any? {
        val all = sharedPreferences.all
        return all.filterKeys { it.contains(keyContains, ignoreCase = true) }.values.firstOrNull()
    }

    fun getHashMapValue(keyContains: String): String?{
        val all = sharedPreferences.all
        val value = getHashMapCategory(keyContains)

        var keyValue = ""
        for ((key, v) in all) {
            if (value == v) {
                keyValue = key
            }
        }
        return sharedPreferences.getString(keyValue, "")
    }

    fun getHashMapTitle(keyContains: String):String?{
        val all = sharedPreferences.all
        val value = getHashMapCategory(keyContains)
        var keyValue = ""
        for ((key, v) in all) {
            if (value == v) {
                keyValue = key
            }
        }
        return keyValue
    }

    fun retrieveUnread(): String? {
        return sharedPreferences.getString(KEY_UNREAD, "0")
    }

    fun retrieveTokenFirebase(): String? {
        return sharedPreferences.getString(KEY_TOKEN_FIREBASE, "firebase")
    }

    fun retrieveSearch(): String? {
        return sharedPreferences.getString(KEY_SEARCH, "")
    }

    fun retrieveHarvestDate(): String? {
        return sharedPreferences.getString(KEY_HARVEST_DATE, "")
    }

    fun retrieveAccessToken(): String? {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, "")
    }

    fun retrieveEmail(): String? {
        return sharedPreferences.getString(KEY_EMAIL, "")
    }

    fun retrieveNameUser(): String? {
        return sharedPreferences.getString(KEY_NAME_USER, "")
    }

    fun setIsLogin(isLogin: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_LOGIN, isLogin).apply()
    }

    fun retrieveIsLogin(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGIN, true)
    }

    fun setisOnBoarding(isOnBoarding: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_ONBOARDING, isOnBoarding).apply()
    }

    fun retrieveisOnBoarding(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_ONBOARDING, false)
    }

    fun retrieveCountCart(): Int {
        return sharedPreferences.getInt(KEY_CART, 0)
    }

    fun setIsNotification(isActive: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_NOTIF, isActive).apply()
    }

    fun retrieveNotifIsActive(): Boolean {
        return sharedPreferences.getBoolean(KEY_NOTIF, true)
    }

    fun setIsOnBoardingSocialBuying(isOnBoarding: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_ONBOARDING_SOCIAL_BUYING, isOnBoarding).apply()
    }

    fun retrieveIsOnBoardingSocialBuying(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_ONBOARDING_SOCIAL_BUYING, false)
    }

    fun setIsCoachMarkSocialBuying(isOnBoarding: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_COACH_MARK_SOCIAL_BUYING, isOnBoarding).apply()
    }

    fun retrieveIsCoachMarkSocialBuying(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_COACH_MARK_SOCIAL_BUYING, false)
    }

    fun setIsCoachMarkSocialBuyingButton(isOnBoarding: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_COACH_MARK_SOCIAL_BUYING_BUTTON, isOnBoarding)
            .apply()
    }

    fun retrieveIsCoachMarkSocialBuyingButton(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_COACH_MARK_SOCIAL_BUYING_BUTTON, false)
    }

    fun setIsCoachMarkCashbackSocialBuying(isCashback: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_COACH_MARK_CASHBACK_SOCIAL_BUYING, isCashback)
            .apply()
    }

    fun retrieveIsCoachMarkCashbackSocialBuying(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_COACH_MARK_CASHBACK_SOCIAL_BUYING, false)
    }

    fun setIsSocialBuying(harvestDate: String, isSocialBuying: Boolean) {
        sharedPreferences.edit().putBoolean(harvestDate, isSocialBuying).apply()
    }

    fun retrieveIsSocialBuying(harvestDate: String): Boolean {
        return sharedPreferences.getBoolean(harvestDate, false)
    }

    fun setSocialBuyingQuantity(harvestDate: String, quantity: String) {
        sharedPreferences.edit().putString(KEY_IS_ONBOARDING_QUANTITY + harvestDate, quantity)
            .apply()
    }

    fun retrieveSocialBuyingQuantity(harvestDate: String): String? {
        return sharedPreferences.getString(KEY_IS_ONBOARDING_QUANTITY + harvestDate, "0")
    }

    fun setIsWelcomeSocialBuying(isSocialBuying: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_WELCOME_SOCIAL_BUYING, isSocialBuying).apply()
    }

    fun retrieveIsWelcomeSocialBuying(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_WELCOME_SOCIAL_BUYING, false)
    }

    fun setIsFirstRatingHome(isRatingHome: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_COACH_MARK_RATING_HOME, isRatingHome).apply()
    }

    fun retrieveIsRatingHome(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_COACH_MARK_RATING_HOME, false)
    }

    fun setIsFirstRatingDetail(isRatingDetail: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_COACH_MARK_RATING_DETAIL, isRatingDetail).apply()
    }

    fun retrieveIsRatingDetail(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_COACH_MARK_RATING_DETAIL, false)
    }

    fun setIsFirstCoupon(isFirstCoupon: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_COACH_MARK_FIRST_COUPON, isFirstCoupon).apply()
    }

    fun retrieveIsFirstCoupon(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_COACH_MARK_FIRST_COUPON, false)
    }

    fun setIsCouponDetail(isCouponDetail: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_COACH_MARK_COUPON_DETAIL, isCouponDetail).apply()
    }

    fun retrieveIsCouponDetail(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_COACH_MARK_COUPON_DETAIL, false)
    }
}