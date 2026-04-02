<script setup lang="ts">
import { ref } from 'vue'

import UiCheckbox from '@/components/ui/UiCheckbox.vue'

const on = ref(false)
</script>

<template>
  <UiCheckbox v-model:checked="on"> Accept terms </UiCheckbox>
</template>
