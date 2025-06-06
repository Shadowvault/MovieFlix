package com.shadowvault.movie_details.data.model.movie_details

import com.shadowvault.core.data.util.FlexibleValueDto
import com.shadowvault.core.data.util.ObjectOrStringSerializer
import kotlinx.serialization.KSerializer

object CollectionValueSerializer : KSerializer<FlexibleValueDto<CollectionDto>> by
ObjectOrStringSerializer(CollectionDto.serializer())