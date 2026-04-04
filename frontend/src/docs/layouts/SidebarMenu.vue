<script setup lang="ts">
import { cn } from '@/ui/lib/utils'
import { computed } from 'vue'
import { RouterLink, useRoute } from 'vue-router'

type Item = { title: string; href: string }
type Group = { title: string; items: Item[] }

const route = useRoute()

const guideGroups: Group[] = [
  {
    title: 'Getting Started',
    items: [{ title: 'Introduction', href: '/docs/guide/introduction' }],
  },
]

function item(title: string, slug: string): Item {
  return { title, href: `/docs/ui/${slug}` }
}

/** Mirrors `frontend-sample` DocsLayout SidebarMenu (UI module). Extra Esab pages at the end. */
const uiGroups: Group[] = [
  {
    title: 'Layout',
    items: [item('Sidebar', 'sidebar')],
  },
  {
    title: 'Display',
    items: [
      item('Accordion', 'accordion'),
      item('Avatar', 'avatar'),
      item('Separator', 'separator'),
    ],
  },
  {
    title: 'Buttons',
    items: [item('Button', 'button'), item('FileTrigger', 'file-trigger')],
  },
  {
    title: 'Collections',
    items: [
      item('Table', 'table'),
      item('DataTable', 'data-table'),
      item('Pagination', 'pagination'),
      item('Searchfield', 'search-field'),
    ],
  },
  {
    title: 'Date and Time',
    items: [
      item('Calendar', 'calendar'),
      item('RangeCalendar', 'range-calendar'),
      item('DatePicker', 'date-picker'),
      item('DateRangePicker', 'date-range-picker'),
      item('DateField', 'date-field'),
      item('TimeField', 'time-field'),
    ],
  },
  {
    title: 'Form Fields',
    items: [
      item('Input', 'input'),
      item('Textarea', 'textarea'),
      item('NumberField', 'number-field'),
      item('Checkbox', 'checkbox'),
      item('RadioGroup', 'radio-group'),
      item('Switch', 'switch'),
      item('Uploader', 'uploader'),
    ],
  },
  {
    title: 'Overlays',
    items: [
      item('Dialog', 'dialog'),
      item('Sheet', 'sheet'),
      item('ConfirmDialog', 'confirm-dialog'),
      item('Menu', 'menu'),
      item('Popover', 'popover'),
      item('Tooltip', 'tooltip'),
      item('Select', 'select'),
    ],
  },
  {
    title: 'Feedback',
    items: [
      item('Sonner', 'sonner'),
      item('Spinner', 'spinner'),
      item('LoadingOverlay', 'loading-overlay'),
      item('NProgress', 'nprogress'),
      item('Skeleton', 'skeleton'),
    ],
  },
]

const groups = computed(() => (route.path.includes('/docs/ui') ? uiGroups : guideGroups))
</script>

<template>
  <div class="docs-sidebar-scroll px-3 pb-8 pt-2 overflow-y-auto max-h-[calc(100vh-8rem)]">
    <div v-for="group in groups" :key="group.title" class="mb-6">
      <div class="px-3 mb-2 text-xs font-medium text-muted-foreground tracking-wide uppercase">
        {{ group.title }}
      </div>
      <ul class="list-none space-y-0.5">
        <li v-for="item in group.items" :key="item.href">
          <RouterLink
            :to="item.href"
            :class="
              cn(
                'block rounded-md px-3 py-1.5 text-[13px] text-secondary-foreground no-underline hover:bg-sidebar-accent',
                route.path === item.href &&
                  'bg-sidebar-accent font-medium text-sidebar-accent-foreground',
              )
            "
          >
            {{ item.title }}
          </RouterLink>
        </li>
      </ul>
    </div>
  </div>
</template>
