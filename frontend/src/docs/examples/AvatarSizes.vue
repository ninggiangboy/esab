<script setup lang="ts">
import UiAvatar from '@/components/ui/UiAvatar.vue'
import { cn } from '@/lib/utils'
</script>

<template>
  <div class="flex items-end gap-3">
    <UiAvatar src="https://github.com/shadcn.png" alt="User" fallback="S" :class="cn('size-6!')" />
    <UiAvatar src="https://github.com/shadcn.png" alt="User" fallback="M" :class="cn('size-8!')" />
    <UiAvatar src="https://github.com/shadcn.png" alt="User" fallback="L" :class="cn('size-10!')" />
  </div>
</template>
