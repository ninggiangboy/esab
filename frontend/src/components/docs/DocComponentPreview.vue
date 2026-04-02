<script setup lang="ts">
import { Check, Copy } from 'lucide-vue-next'
import { ref, watch } from 'vue'

import { useHtmlDark } from '@/composables/useHtmlDark'
import { highlightVueSource } from '@/lib/shiki-highlighter'
import { cn } from '@/lib/utils'

import UiButton from '@/components/ui/UiButton.vue'

const props = defineProps<{
  /** Optional Tailwind classes for the live preview area */
  containerClass?: string
  /**
   * Base name of the file under `src/docs/examples/` (e.g. `ButtonDemo`) to show source for.
   * When set, the `.vue` source is loaded with `?raw` and rendered below the preview.
   */
  example?: string
}>()

const rawModules = import.meta.glob<string>('../../docs/examples/**/*.vue', {
  query: '?raw',
  import: 'default',
})

function findRawLoader(name: string | undefined) {
  if (!name) return undefined
  const suffix = `/${name}.vue`
  for (const key of Object.keys(rawModules)) {
    if (key.endsWith(suffix)) return rawModules[key]
  }
  return undefined
}

const source = ref<string | null>(null)
const loadError = ref<string | null>(null)
const copied = ref(false)

const isDark = useHtmlDark()
const highlightedHtml = ref<string | null>(null)
const highlightPending = ref(false)

watch(
  () => props.example,
  async (name) => {
    source.value = null
    loadError.value = null
    highlightedHtml.value = null
    if (!name) return
    const loader = findRawLoader(name)
    if (!loader) {
      loadError.value = `Example not found: ${name}.vue`
      return
    }
    try {
      source.value = await loader()
    } catch {
      loadError.value = 'Could not load source.'
    }
  },
  { immediate: true },
)

watch(
  [source, isDark],
  async ([code, dark]) => {
    highlightedHtml.value = null
    if (!code) return
    highlightPending.value = true
    try {
      highlightedHtml.value = await highlightVueSource(code, dark)
    } catch {
      highlightedHtml.value = null
    } finally {
      highlightPending.value = false
    }
  },
  { immediate: true },
)

async function copyCode() {
  const text = source.value
  if (!text || !navigator.clipboard?.writeText) return
  try {
    await navigator.clipboard.writeText(text)
    copied.value = true
    window.setTimeout(() => {
      copied.value = false
    }, 2000)
  } catch {
    /* ignore */
  }
}
</script>

<template>
  <div class="not-prose mt-4 w-full border-b pb-8 pt-0 lg:mt-6">
    <div class="space-y-1.5 rounded-xl border bg-background p-1.5">
      <div :class="cn('flex min-h-[100px] items-center justify-center p-5', containerClass)">
        <slot />
      </div>

      <div
        v-if="example"
        class="relative overflow-hidden rounded-md border bg-background-secondary"
      >
        <UiButton
          variant="ghost"
          size="iconSm"
          type="button"
          class="absolute right-5 top-2 z-[1] size-8 bg-background/80 shadow-sm backdrop-blur-sm"
          :aria-label="copied ? 'Copied' : 'Copy code'"
          :disabled="!source"
          @click="copyCode"
        >
          <Check v-if="copied" class="size-4 text-green-600" />
          <Copy v-else class="size-4" />
        </UiButton>

        <div v-if="source" class="max-h-[min(560px,75vh)] overflow-auto overscroll-contain">
          <p v-if="highlightPending" class="m-0 px-4 pt-12 pb-2 text-xs text-muted-foreground">
            Syntax highlighting…
          </p>
          <div
            v-else-if="highlightedHtml"
            class="doc-preview-shiki px-4 pb-4 pt-12"
            v-html="highlightedHtml"
          />
          <pre
            v-else
            class="m-0 min-w-0 px-4 pb-4 pt-12 text-left font-mono text-[13px] leading-relaxed whitespace-pre text-foreground"
          ><code>{{ source }}</code></pre>
        </div>

        <p v-else-if="loadError" class="m-0 p-4 text-sm text-destructive">
          {{ loadError }}
        </p>
        <p v-else class="m-0 p-4 text-sm text-muted-foreground">Loading source…</p>
      </div>
    </div>
  </div>
</template>
