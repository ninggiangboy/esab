<script setup lang="ts">
import { docFormToast } from '@/docs/examples/_internal/docFormSubmit'
import { toTypedSchema } from '@vee-validate/zod'
import { z } from 'zod'
import { Button } from '@/ui/components/button'
import {
  Form,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  FormVm,
  useForm,
} from '@/ui/components/form'
import { NumberField } from '@/ui/components/numberfield'

const schema = toTypedSchema(z.object({ qty: z.number().min(1) }))
const { handleSubmit } = useForm({ validationSchema: schema, initialValues: { qty: 1 } })
const onSubmit = handleSubmit((v) => docFormToast(v))
</script>
<template>
  <Form class="w-full max-w-xs space-y-4" @submit="onSubmit">
    <FormField v-slot="{ componentField }" name="qty">
      <FormItem>
        <FormLabel>Quantity</FormLabel>
        <FormVm generic="number | null" v-slot="vm" :component-field="componentField">
          <NumberField v-bind="vm" />
        </FormVm>
        <FormMessage />
      </FormItem>
    </FormField>
    <Button type="submit">Submit</Button>
  </Form>
</template>
