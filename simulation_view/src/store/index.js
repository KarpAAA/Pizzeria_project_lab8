import {createStore} from 'vuex'
import axios from "axios";

const store = createStore({
    state() {
        return {
            restaurant: {
                paydesks: []
            }
        }
    },
    actions: {
        getRestaurant({state}) {
            axios.get("http://localhost:8081/restaurant",
                {
                    headers: {
                        "Content-Type": "application/json"
                    }
                })
                .catch(error => {
                    console.error(error);
                });
        },
    },
    mutations: {
        setPaydesks(state, paydesks) {
            state.restaurant.paydesks = paydesks;
        }
    }
})

export default store;