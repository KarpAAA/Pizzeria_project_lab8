<template>
  <div class="container">
    <div class="paydesks">
      <div v-for="p in paydesks">
        <paydesk-component :paydesk="p"></paydesk-component>
      </div>
    </div>


    <div>
      <div v-for="p in paydesks">
        <div v-for="c in p.clients">
          <client-component :client="c" :key="c.id"></client-component>
        </div>
      </div>

    </div>
    <div class="messages" id="messages">
    </div>
  </div>
</template>

<script>
import PaydeskComponent from "@/components/Paydesk.vue";
import {mapState} from 'vuex'
import ClientComponent from "@/components/Client.vue";

export default {
  name: "map-component",
  components: {ClientComponent, PaydeskComponent},
  computed: {
    ...mapState({
      paydesks: state => state.restaurant.paydesks,
    })
  }
}
</script>

<style scoped>
.paydesks {
  width: 100%;
  height: 145px;
  position: fixed;
  background-color: #d6b99a;
  z-index: 1;
}
.messages {
  z-index: 2;
  width: 250px;
  position: fixed;
  top: 150px;
  left: 1250px;
}

.container {
  overflow-x: hidden;
  position: relative;
  width: 100vw;
  height: 100vh;
}

.container::-webkit-scrollbar {
  display: none;
}
</style>
