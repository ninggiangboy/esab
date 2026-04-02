<script setup lang="ts">
import { ref } from 'vue'

import UiLabel from '@/components/ui/UiLabel.vue'
import UiRadioGroup from '@/components/ui/UiRadioGroup.vue'
import UiRadioItem from '@/components/ui/UiRadioItem.vue'

const value = ref('left')
</script>

<template>
  <UiRadioGroup v-model="value" orientation="horizontal" class="flex flex-wrap gap-4">
    <label class="flex cursor-pointer items-center gap-2 text-sm">
      <UiRadioItem value="left" />
      <UiLabel class="cursor-pointer font-normal">Left</UiLabel>
    </label>
    <label class="flex cursor-pointer items-center gap-2 text-sm">
      <UiRadioItem value="right" />
      <UiLabel class="cursor-pointer font-normal">Right</UiLabel>
    </label>
  </UiRadioGroup>
</template>
