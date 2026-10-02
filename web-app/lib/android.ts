import type {CourseState} from './course';
declare global {interface Window{PepperAndroid?:{saveState:(json:string)=>void;requestNotifications:()=>void;getState:()=>string};onPepperPermission?:(granted:boolean)=>void;}}
export const isAndroidApp=()=>typeof window!=='undefined'&&!!window.PepperAndroid;
export function syncAndroid(state:CourseState){window.PepperAndroid?.saveState(JSON.stringify(state));}
export function requestAndroidNotifications():Promise<boolean>{return new Promise(resolve=>{window.onPepperPermission=granted=>{delete window.onPepperPermission;resolve(granted)};window.PepperAndroid?.requestNotifications();});}
