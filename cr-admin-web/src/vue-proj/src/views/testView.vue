<template>
  <div>
    <button ref="trigger" @click="toggleTooltip">Toggle Tooltip</button>
    <div ref="tooltip" v-show="showTooltip">This is a tooltip!</div>
  </div>
</template>

<script>
import Popper from "popper.js";
import "tooltip.js";

export default {
  data() {
    return {
      showTooltip: false,
      popperInstance: null,
    };
  },
  methods: {
    toggleTooltip() {
      this.showTooltip = !this.showTooltip;

      if (this.showTooltip) {
        this.createPopper();
      } else {
        this.destroyPopper();
      }
    },
    createPopper() {
      this.popperInstance = new Popper(this.$refs.trigger, this.$refs.tooltip, {
        placement: "bottom",
      });
    },
    destroyPopper() {
      if (this.popperInstance) {
        this.popperInstance.destroy();
        this.popperInstance = null;
      }
    },
  },
};
</script>
