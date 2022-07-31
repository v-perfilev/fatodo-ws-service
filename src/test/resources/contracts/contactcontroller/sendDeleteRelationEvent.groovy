package contracts.contactcontroller

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name 'send delete relation event'
    description 'should return status 200'
    request {
        method POST()
        url("/api/contact/delete-relation")
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
                "content": $(
                        consumer(anyUuid()),
                        producer(uuid().generate())
                ),
        )
        bodyMatchers {
            jsonPath('$.userIds', byType {
                occurrence(1)
            })
        }
    }
    response {
        status 200
    }
}
