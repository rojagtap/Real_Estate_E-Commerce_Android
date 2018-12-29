package com.axiomcorp.rohan.rees

public class Property {
    var id: String? = null
    var title: String? = null
    var locality: String? = null
    var propertyType: String? = null
    var price: Int? = 0
    var BHK: String? = null
    var constructionStatus: String? = null
    var area: Int? = 0
    var address: String? = null
    var description: String? = null
    var image: Int? = 0


    constructor() {
        this.id = null
        this.title = null
        this.locality = null
        this.propertyType = null
        this.price = 0
        this.BHK = null
        this.constructionStatus = null
        this.area = 0
        this.address = null
        this.description = null
        this.image = 0
    }

    constructor(id: String?, title: String?, locality: String?, propertyType: String?, price: Int?,
                BHK: String?, constructionStatus: String?, area: Int?, address: String?, description: String?, image: Int?) {
        this.id = id
        this.title = title
        this.locality = locality
        this.propertyType = propertyType
        this.price = price
        this.BHK = BHK
        this.constructionStatus = constructionStatus
        this.area = area
        this.address = address
        this.description = description
        this.image = image
    }
}