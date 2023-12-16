<template>
  <div :style="paydeskStyles">

    <div class="screen ">
      <div class="screen_orders">
        {{ getCommaSeparatedOrderNumbers(paydesk.clients) }}
      </div>
    </div>
    <div :style="{
        left: paydesk.position.x,
         top: paydesk.position.y + 70,
          backgroundColor: '#525252',
           width: '111px',
            height: '30px',
             position: 'relative',
             borderRadius: '0 0 10px 10px'
           }"
    >
      <div style="margin-left: 26px; padding-top: 3px; color: white">
        {{ paydesk.name }}
      </div>

    </div>
  </div>

</template>

<script>
import ClientComponent from "@/components/Client.vue";
import tv from "@/assets/tv3.png";

export default {
  name: "paydesk-component",
  components: {ClientComponent},
  props: {
    paydesk: {required: true}
  },
  methods: {
    getCommaSeparatedOrderNumbers(clients) {
      let numbers = clients.map((client) => client.order.number);
      return numbers.join(", ");
    },
  },
  computed: {

    paydeskStyles() {
      return {
        position: 'absolute',
        width: `${this.paydesk.position.width}px`,
        height: `${this.paydesk.position.height - 30}px`,
        top: `${this.paydesk.position.y}px`,
        left: `${this.paydesk.position.x}px`,
        backgroundImage: `url(${tv})`
      };
    }
  }
}
</script>

<style scoped>
.screen_orders {
  padding-top: 7px;
  margin-left: 15px;
  margin-right: 15px;
}

.screen {
  overflow: scroll;
  border-radius: 10px;
  width: 100%;
  height: 100%;
  color: black;
}

.screen::-webkit-scrollbar {
  display: none;
}
</style>