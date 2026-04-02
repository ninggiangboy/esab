<script setup lang="ts">
import { ref } from 'vue'

import UiCheckbox from '@/components/ui/UiCheckbox.vue'

const state = ref<boolean | 'indeterminate'>('indeterminate')
</script>

<template>
  <UiCheckbox v-model:checked="state"> Select all </UiCheckbox>
</template>
