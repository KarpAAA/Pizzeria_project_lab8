import {Stomp} from "@stomp/stompjs";
import SockJS from "sockjs-client";
import store from "@/store";

export var stompClient = null;

export function connect() {
    console.log('trying connect0');
    let socket = new SockJS('http://localhost:8082/ws');
    stompClient = Stomp.over(socket);
    stompClient.activate();
    console.log('trying connect');
    stompClient.onConnect = (frame) => {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/map', (message) => {
            const res = JSON.parse(message.body);
            store.commit('setPaydesks', res.paydesks)
        });
        stompClient.subscribe('/topic/event', (message) => {
            createMessage(message.body, false);
        });
        stompClient.subscribe('/topic/event/failed', (message) => {

            createMessage(message.body, true);
        });
        store.dispatch('getRestaurant');
    };

    stompClient.onWebSocketError = (error) => {
        console.error('Error with websocket', error);
    };

    stompClient.onStompError = (frame) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
    };
    stompClient.onConnecting = () => {
        console.log('Connecting...');
    };

    stompClient.onDisconnected = () => {
        console.log('Disconnected');
    };
}
function createMessage(message, failed) {
    const messageElement = document.createElement("div");

    messageElement.textContent = message;
    messageElement.style.marginBottom = "10px";
    messageElement.style.padding = "10px";
    messageElement.style.color = "white";
    messageElement.style.borderRadius = "15px";
    if(failed) messageElement.style.backgroundColor = "red";
    else messageElement.style.backgroundColor = "green";

    messageElement.style.opacity = "0.9";

    setTimeout(() => {
        messageElement.remove();
    }, 3000);

    document.getElementById("messages").appendChild(messageElement);
}








