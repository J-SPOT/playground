import type { Metadata } from 'next'
import { Inter } from 'next/font/google'
import './globals.css'
import {
  useQueryClient,
  QueryClientProvider,
} from '@tanstack/react-query'

const inter = Inter({ subsets: ['latin'] })

export const metadata: Metadata = {
  title: 'Funny Board',
  description: "J-SPOT's Playground",
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {

  const queryClient = useQueryClient()

  return (
    <QueryClientProvider client={queryClient}>
      <html lang="en">
        <body className={inter.className}>{children}</body>
      </html>
    </QueryClientProvider>
  )
}
