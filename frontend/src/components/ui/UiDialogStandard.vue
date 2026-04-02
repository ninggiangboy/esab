<script setup lang="ts">
import {
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogOverlay,
  DialogPortal,
  DialogRoot,
  DialogTitle,
  DialogTrigger,
} from 'radix-vue'
import { X } from 'lucide-vue-next'

import { cn } from '@/lib/utils'

import UiButton from './UiButton.vue'

const open = defineModel<boolean>({ default: false })
</script>

<template>
  <DialogRoot v-model:open="open">
    <DialogTrigger as-child>
      <slot name="trigger" />
    </DialogTrigger>
    <DialogPortal>
      <DialogOverlay
        :class="
          cn(
            'fixed inset-0 z-50 bg-black/70',
            'data-[state=closed]:animate-out data-[state=closed]:fade-out-0',
            'data-[state=open]:animate-in data-[state=open]:fade-in-0',
          )
        "
      />
      <DialogContent
        :class="
          cn(
            'fixed left-[50vw] top-1/2 z-50 grid w-full max-w-[calc(100vw-40px)] -translate-x-1/2 -translate-y-1/2 gap-4 rounded-xl bg-background p-5 shadow-2xl outline-none md:max-w-lg dark:border',
            'data-[state=open]:animate-in data-[state=closed]:animate-out data-[state=open]:fade-in-0 data-[state=closed]:fade-out-0',
            'md:data-[state=open]:zoom-in-97 md:data-[state=closed]:zoom-out-97',
          )
        "
      >
        <DialogTitle class="text-lg font-semibold leading-none tracking-tight">
          <slot name="title" />
        </DialogTitle>
        <DialogDescription v-if="$slots.description" class="text-sm text-muted-foreground">
          <slot name="description" />
        </DialogDescription>
        <slot />
        <DialogClose as-child>
          <UiButton
            variant="ghost"
            size="icon"
            class="absolute right-2.5 top-2.5"
            aria-label="Close"
          >
            <X class="size-4! text-muted-foreground" />
          </UiButton>
        </DialogClose>
      </DialogContent>
    </DialogPortal>
  </DialogRoot>
</template>
