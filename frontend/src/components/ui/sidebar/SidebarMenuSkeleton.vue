<script setup lang="ts">
import { onMounted, ref } from 'vue'

import { cn } from '@/lib/utils'

import UiSkeleton from '../UiSkeleton.vue'

withDefaults(
  defineProps<{
    showIcon?: boolean
  }>(),
  { showIcon: false },
)

const width = ref('70%')
onMounted(() => {
  width.value = `${Math.floor(Math.random() * 40) + 50}%`
})
</script>

<template>
  <div
    data-slot="sidebar-menu-skeleton"
    data-sidebar="menu-skeleton"
    :class="cn('flex h-8 items-center gap-2 rounded-md px-2', $attrs.class as string | undefined)"
  >
    <UiSkeleton v-if="showIcon" class="size-4 rounded-md" data-sidebar="menu-skeleton-icon" />
    <UiSkeleton
      class="h-4 max-w-(--skeleton-width) flex-1"
      data-sidebar="menu-skeleton-text"
      :style="{ '--skeleton-width': width }"
    />
  </div>
</template>
