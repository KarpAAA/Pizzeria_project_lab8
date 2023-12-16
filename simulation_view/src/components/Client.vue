<template>

  <div :style="clientStyles">
<!--    <div class="progress-bar" :style="{ width: `${client.orderProgress}%`, backgroundColor:'green' }">-->
<!--      -->
<!--    </div>-->
    <div v-if='client.mood > 70' class="progress-bar" :style="{ width: `${client.mood}%`, backgroundColor:'green' }"></div>
    <div v-else-if="client.mood > 30" class="progress-bar" :style="{ width: `${client.mood}%`, backgroundColor:'orange' }"></div>
    <div v-else class="progress-bar" :style="{ width: `${client.mood}%`, backgroundColor:'red' }"></div>
  </div>
</template>

<script>
import c1 from "@/assets/charactes/pavlo.png";
import c2 from "@/assets/charactes/vutalyk.png";
import c3 from "@/assets/charactes/ivan.png";
import c4 from "@/assets/charactes/nika.png";
import c5 from "@/assets/charactes/kyma.png";
import c6 from "@/assets/charactes/bee.png";

export default {
  name: "client-component",
  data() {
    return {
      girlQueueImages: [
        c1,
        c2,
        c3,
        c4,
        c5,
        c6,
      ],
    }
  },
  props: {
    client: {required: true}
  },
  computed: {
    image() {
      const randomIndex = this.client.order.number % this.girlQueueImages.length;
      return this.girlQueueImages[randomIndex];
    },
    clientStyles() {
      return {
        position: 'absolute',
        width: `${this.client.position.width}px`,
        height: `${this.client.position.height}px`,
        top: `${this.client.position.y}px`,
        left: `${this.client.position.x - 10}px`,
        backgroundImage: `url(${this.image})`
      };
    }
  }
}
</script>

<style scoped>

.progress-bar {
  position: relative;
  top: -9px;
  height: 10px;
  width: 100%;
  border-radius: 5px;
}

.progress-bar::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 10px;
  border-radius: 5px;
  opacity: 0.8;
}

</style>
