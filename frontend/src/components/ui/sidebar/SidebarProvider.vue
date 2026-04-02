<script setup lang="ts">
import { useEventListener } from '@vueuse/core'
import { computed, provide, ref, watch } from 'vue'

import { useIsMobile } from '@/composables/useIsMobile'
import { cn } from '@/lib/utils'

import {
  SIDEBAR_COOKIE_MAX_AGE,
  SIDEBAR_COOKIE_NAME,
  SIDEBAR_KEYBOARD_SHORTCUT,
  SIDEBAR_WIDTH,
  SIDEBAR_WIDTH_ICON,
} from './constants'
import { sidebarInjectionKey } from './context'

const props = withDefaults(
  defineProps<{
    defaultOpen?: boolean
    open?: boolean
    class?: string
  }>(),
  { defaultOpen: true },
)

const emit = defineEmits<{
  'update:open': [value: boolean]
}>()

const isMobile = useIsMobile()
const openMobile = ref(false)
const _open = ref(props.defaultOpen ?? true)

watch(
  () => props.defaultOpen,
  (v) => {
    if (props.open === undefined) {
      _open.value = v ?? true
    }
  },
)

const openRef = computed(() => (props.open !== undefined ? props.open : _open.value))

function setOpen(value: boolean | ((prev: boolean) => boolean)) {
  const current = openRef.value
  const next = typeof value === 'function' ? value(current) : value
  document.cookie = `${SIDEBAR_COOKIE_NAME}=${next}; path=/; max-age=${SIDEBAR_COOKIE_MAX_AGE}`
  if (props.open !== undefined) {
    emit('update:open', next)
  } else {
    _open.value = next
  }
}

function setOpenMobile(value: boolean | ((prev: boolean) => boolean)) {
  const current = openMobile.value
  openMobile.value = typeof value === 'function' ? value(current) : value
}

function toggleSidebar() {
  if (isMobile.value) {
    setOpenMobile((o) => !o)
  } else {
    setOpen((o) => !o)
  }
}

const state = computed(() => (openRef.value ? 'expanded' : 'collapsed'))

useEventListener(window, 'keydown', (event) => {
  if (event.key === SIDEBAR_KEYBOARD_SHORTCUT && (event.metaKey || event.ctrlKey)) {
    event.preventDefault()
    toggleSidebar()
  }
})

provide(sidebarInjectionKey, {
  state,
  open: openRef,
  setOpen,
  openMobile,
  setOpenMobile,
  isMobile,
  toggleSidebar,
})
</script>

<template>
  <div
    data-slot="sidebar-wrapper"
    :style="{
      '--sidebar-width': SIDEBAR_WIDTH,
      '--sidebar-width-icon': SIDEBAR_WIDTH_ICON,
    }"
    :class="
      cn(
        'group/sidebar-wrapper has-data-[variant=inset]:bg-sidebar flex min-h-svh w-full',
        props.class,
      )
    "
  >
    <slot />
  </div>
</template>
