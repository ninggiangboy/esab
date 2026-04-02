<script setup lang="ts">
import { Pencil } from 'lucide-vue-next'

import UiButton from '@/components/ui/UiButton.vue'
import UiTooltip from '@/components/ui/UiTooltip.vue'
</script>

<template>
  <UiTooltip :delay-duration="700" variant="inverse">
    <template #trigger>
      <UiButton variant="outline" size="icon" aria-label="Edit">
        <Pencil class="size-4" />
      </UiButton>
    </template>
    Edit
  </UiTooltip>
</template>
