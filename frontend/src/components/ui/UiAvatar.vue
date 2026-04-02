<script setup lang="ts">
import { AvatarFallback, AvatarImage, AvatarRoot } from 'radix-vue'

import { cn } from '@/lib/utils'

const props = defineProps<{
  src?: string
  alt?: string
  fallback?: string
}>()
</script>

<template>
  <AvatarRoot
    data-slot="avatar"
    :class="
      cn('relative flex size-7 shrink-0 overflow-hidden rounded-full', $attrs.class as string)
    "
  >
    <AvatarImage
      v-if="props.src"
      data-slot="avatar-image"
      :src="props.src"
      :alt="props.alt"
      class="aspect-square size-full object-cover"
    />
    <AvatarFallback
      data-slot="avatar-fallback"
      class="bg-sky-500 flex size-full items-center justify-center rounded-full text-white"
    >
      <slot name="fallback">{{ fallback }}</slot>
    </AvatarFallback>
  </AvatarRoot>
</template>
