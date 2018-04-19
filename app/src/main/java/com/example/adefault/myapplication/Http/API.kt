package com.example.adefault.myapplication.Http

/**
 * Created by CDI on 2/27/18
 */
object API {
    private const val BASE_URL      = "http://192.168.0.121:8000"
    const val LOGIN                 = "$BASE_URL/auth/login"
    const val LOGOUT                = "$BASE_URL/auth/logout"
    const val REGISTER              = "$BASE_URL/user"
    const val RECEIVE               = "$BASE_URL/inbound/scan"
    const val LOAD                  = "$BASE_URL/load/scan"
    const val LOADED                = "$BASE_URL/loaded/scan"
    const val OUTBOUND              = "$BASE_URL/outbound/scan"
    const val SORTBASKET            = "$BASE_URL/sort/basket"
    const val SORTROLLCAGE          = "$BASE_URL/sort/rollcage"

    const val ALLITEMS              = "$BASE_URL/package/all"
    const val INCOMINGITEMS         = "$BASE_URL/package/for_inbound"
    const val RECEIVEDITEMS         = "$BASE_URL/package/received"
    const val LOADEDITEMS           = "$BASE_URL/loaded/retrieve/all"
    const val OUTBOUNDITEMS         = "$BASE_URL/outbound/retrieve/all"
    const val LOADITEMS             = "$BASE_URL/load/retrieve/all"
    const val SORTEDITEMS           = "$BASE_URL/sort/check/all"


}