import SockJS from "sockjs-client";
import Stomp from "stompjs";

let sock = new SockJS("http://localhost:8085/chat");
let client = Stomp.over(sock);
console.log("appp.js")

client.connect({}, frame => {
console.log("Successfully connected")

});

