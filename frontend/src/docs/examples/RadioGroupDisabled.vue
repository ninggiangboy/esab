<script setup lang="ts">
import { ref } from 'vue'

import UiLabel from '@/components/ui/UiLabel.vue'
import UiRadioGroup from '@/components/ui/UiRadioGroup.vue'
import UiRadioItem from '@/components/ui/UiRadioItem.vue'

const value = ref('one')
</script>

<template>
  <UiRadioGroup v-model="value" disabled class="flex flex-col gap-2">
    <label class="flex cursor-not-allowed items-center gap-2 text-sm opacity-70">
      <UiRadioItem value="one" disabled />
      <UiLabel class="font-normal">One</UiLabel>
    </label>
    <label class="flex cursor-not-allowed items-center gap-2 text-sm opacity-70">
      <UiRadioItem value="two" disabled />
      <UiLabel class="font-normal">Two</UiLabel>
    </label>
  </UiRadioGroup>
</template>
