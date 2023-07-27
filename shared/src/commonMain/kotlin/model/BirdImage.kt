/*
 * Created by Ali Al Fayed on 7/29/23, 12:00 PM.
 *
 * Copyright (c) 2023  Ali Al Fayed. All rights reserved.
 * Last modified: 7/31/23, 5:57 PM
 *
 * LinkedIn: https://www.linkedin.com/in/alfayedoficial/
 */

package model

import kotlinx.serialization.Serializable

@Serializable
data class BirdImage(
	val category: String?= null,
	val path: String?= null,
	val author: String?= null,
)

typealias BirdImages = List<BirdImage>
