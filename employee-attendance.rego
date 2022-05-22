package employee.attendance

default allow = false

allow {
    input.transportHeaders.Origin == "https://localhost:9443"
}

message := verified {
	allow == true
    verified = "Verified with OPA"
}
