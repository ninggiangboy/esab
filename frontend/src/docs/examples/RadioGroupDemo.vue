<script setup lang="ts">
import { ref } from 'vue'

import UiLabel from '@/components/ui/UiLabel.vue'
import UiRadioGroup from '@/components/ui/UiRadioGroup.vue'
import UiRadioItem from '@/components/ui/UiRadioItem.vue'

const value = ref('comfortable')
</script>

<template>
  <UiRadioGroup v-model="value" class="flex flex-col gap-2">
    <label class="flex cursor-pointer items-center gap-2 text-sm">
      <UiRadioItem value="default" />
      <UiLabel class="cursor-pointer font-normal">Default</UiLabel>
    </label>
    <label class="flex cursor-pointer items-center gap-2 text-sm">
      <UiRadioItem value="comfortable" />
      <UiLabel class="cursor-pointer font-normal">Comfortable</UiLabel>
    </label>
    <label class="flex cursor-pointer items-center gap-2 text-sm">
      <UiRadioItem value="compact" />
      <UiLabel class="cursor-pointer font-normal">Compact</UiLabel>
    </label>
  </UiRadioGroup>
</template>
