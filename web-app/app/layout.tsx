import type {Metadata,Viewport} from 'next';import './globals.css';
export const metadata:Metadata={title:'Курс перца горошек — трекер привычки',description:'Личный трекер выбранного 30-дневного курса. Отметки, статистика и ваш ежедневный ритм.',manifest:'/manifest.webmanifest',icons:{icon:'/favicon.svg',apple:'/icon-192.png'},appleWebApp:{capable:true,title:'Курс перца',statusBarStyle:'black-translucent'}};
export const viewport:Viewport={width:'device-width',initialScale:1,themeColor:'#191611'};
export default function RootLayout({children}:{children:React.ReactNode}){return <html lang="ru"><body>{children}</body></html>;}
