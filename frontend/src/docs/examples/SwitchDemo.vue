<script setup lang="ts">
import { ref } from 'vue'

import UiLabel from '@/components/ui/UiLabel.vue'
import UiSwitch from '@/components/ui/UiSwitch.vue'

const on = ref(true)
</script>

<template>
  <label class="flex cursor-pointer items-center justify-between gap-4 text-sm sm:max-w-xs">
    <UiLabel class="cursor-pointer font-normal">Enable notifications</UiLabel>
    <UiSwitch v-model:checked="on" />
  </label>
</template>
