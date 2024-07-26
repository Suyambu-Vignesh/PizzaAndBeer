package com.app.pizzaandbeer.data

interface ProximityBusinessRepository<Request, Response> {
    suspend fun getPizzaOrBeerBusiness(request: Request): Response
}
