import { useGetArticlesQuery } from '@/apis/article/article.query'
import { Inter } from 'next/font/google'

const inter = Inter({ subsets: ['latin'] })

export default function Home() {
  return (
    <main
      className={`flex min-h-screen flex-col items-center justify-between p-24 ${inter.className}`}
    >
      메인 페이지
    </main>
  )
}
