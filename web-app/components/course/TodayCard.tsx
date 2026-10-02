import {Check, Clock, Trophy} from 'lucide-react';
import {pepperCount,pepperLabel} from '@/lib/course';
import {Peppercorns} from './Peppercorns';
import {ProgressBar} from './ProgressBar';
export function TodayCard({day,done,progress,onComplete,onSnooze,expanded=false}:{day:number;done:boolean;progress:number;onComplete:()=>void;onSnooze:()=>void;expanded?:boolean}) { const active=day>=1&&day<=30; const display=Math.min(30,Math.max(1,day));return <article className={`today-card ${done?'is-done':''} ${expanded?'expanded':''}`}>
{expanded&&<div className="inside-progress"><div className="row"><span>День {display} из 30</span><span>{progress}%</span></div><ProgressBar value={progress}/></div>}
<div className="eyebrow dark"><span className="tiny-dot"/> {day<1?'Курс скоро начнется':day>30?'30 дней позади':done?'Сегодня выполнено':'Ваш маленький ежедневный шаг'}</div>
<h2>{day<1?'До встречи':day>=30?'Завершение курса':'Сегодня'}</h2>
<div className="dose">{day>=30?<><Trophy size={52}/><span>{day>30?'Курс окончен':'День 30'}</span></>:<>{pepperCount(display)} <span>{pepperLabel(pepperCount(display)).split(' ')[1]}</span></>}</div>
<div className="pepper-stage">{day>=30?<Trophy className="large-trophy"/>:<Peppercorns count={pepperCount(display)}/>}</div>
<p className="dose-caption">{day>=30?'В последний день — только отметка завершения':day<1?'Начните курс в выбранную дату':'Количество по выбранному курсу'}</p>
<button className="complete-button" onClick={onComplete} disabled={done||!active}>{done?<Check className="check-pop"/>:<Check/>}{done?'Сегодня отмечено':day===30?'Завершить курс':day>30?'Курс окончен':day<1?'Курс еще не начался':'Я принял(а)'}</button>
{expanded&&<button className="snooze-button" onClick={onSnooze} disabled={done||!active}><Clock size={18}/>Напомнить позже</button>}
<div className="today-bottom"><span>{done?'Отметка сохранена': 'Один день. Один небольшой шаг.'}</span>{done&&<Check size={15}/>}</div>
</article>; }
