package contracts.chatcontroller

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name 'send chat last message update event'
    description 'should return status 200'
    request {
        method POST()
        url("/api/chat/last-message-update")
        headers {
            contentType applicationJson()
            header 'Authorization': $(
                    consumer(containing("Bearer")),
                    producer("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI4ZjlhN2NhZS03M2M4LTRhZDYtYjEzNS01YmQxMDliNTFkMmUiLCJ1c2VybmFtZSI6InRlc3RfdXNlciIsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSIiwiaWF0IjowLCJleHAiOjMyNTAzNjc2NDAwfQ.Go0MIqfjREMHOLeqoX2Ej3DbeSG7ZxlL4UAvcxqNeO-RgrKUCrgEu77Ty1vgR_upxVGDAWZS-JfuSYPHSRtv-w")
            )
        }
        body(
                "userIds": $(
                        consumer(any()),
                        producer([uuid().generate()])
                ),
                "chat": [
                        "id"      : $(
                                consumer(anyUuid()),
                                producer(uuid().generate())
                        ),
                        "isDirect": $(
                                consumer(anyBoolean()),
                                producer(anyBoolean().generate())
                        ),
                ],
        )
        bodyMatchers {
            jsonPath('$.userIds', byType {
                minOccurrence(1)
            })
        }
    }
    response {
        status 200
    }
}
