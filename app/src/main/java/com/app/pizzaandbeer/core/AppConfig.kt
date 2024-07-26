package com.app.pizzaandbeer.core

/**
 * Configuration to change/control the experience of the application. This Configuration need to server
 * driven more to control the experience dynamically and handle backward compatibility.
 */
object AppConfig {
    /**
     * Default Longitude, Subjected to Change (at present SF)
     */
    internal const val DEFAULT_LATITUDE: Int = 37

    /**
     * Default Longitude, Subjected to Change (at present SF)
     */
    internal const val DEFAULT_LONGITUDE: Int = 122

    /**
     * Helps to control the number of item per page
     */
    internal const val NUMBER_OF_ITEM_PER_PAGE = 20

    /**
     * Helps to control the API Token
     */
    internal const val API_TOKEN =
        "Bearer 2ROaa2Rh9qu3WVTCms8FoVE4mSfHQHC7QJua95-kKT-PqzIlLSrs4tmHVdtdFw_66-JNfRiJmbCByHTvFNy5dQq-tpfS4FrPpupIzKlgELR3br-r5trpeFhrCRgwWnYx"
}
