<script setup lang="ts">
import { TooltipProvider } from 'radix-vue'
import { Toaster } from 'vue-sonner'

import UiConfirmDialog from '@/components/ui/UiConfirmDialog.vue'
import { provideTheme } from '@/composables/useTheme'

provideTheme('light')
</script>

<template>
  <TooltipProvider :delay-duration="300">
    <RouterView />
    <UiConfirmDialog />
    <Toaster position="bottom-right" />
  </TooltipProvider>
</template>
