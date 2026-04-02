<script setup lang="ts">
import { ref } from 'vue'

import UiButton from '@/components/ui/UiButton.vue'
import UiLabel from '@/components/ui/UiLabel.vue'
import UiPopover from '@/components/ui/UiPopover.vue'
import UiSwitch from '@/components/ui/UiSwitch.vue'

const wifi = ref(true)
const bluetooth = ref(true)
const mute = ref(false)
</script>

<template>
  <UiPopover side="bottom" align="end" :side-offset="8">
    <template #trigger>
      <UiButton variant="outline">Settings</UiButton>
    </template>
    <div class="flex flex-col gap-4">
      <label class="flex cursor-pointer items-center justify-between gap-3 text-sm">
        <UiLabel class="cursor-pointer">Wi‑Fi</UiLabel>
        <UiSwitch v-model:checked="wifi" />
      </label>
      <label class="flex cursor-pointer items-center justify-between gap-3 text-sm">
        <UiLabel class="cursor-pointer">Bluetooth</UiLabel>
        <UiSwitch v-model:checked="bluetooth" />
      </label>
      <label class="flex cursor-pointer items-center justify-between gap-3 text-sm">
        <UiLabel class="cursor-pointer">Mute</UiLabel>
        <UiSwitch v-model:checked="mute" />
      </label>
    </div>
  </UiPopover>
</template>
