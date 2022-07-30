#!/usr/bin/env python
# coding: utf-8

# In[ ]:





# In[ ]:





# In[ ]:





# In[9]:


import numpy as np

import pandas as pd

from plotly.offline import download_plotlyjs, init_notebook_mode

import plotly.express as px

import sqlite3 as sql

import cufflinks as cf 

import chart_studio.plotly as py

import chart_studio.tools as tls

import plotly.graph_objs as go

get_ipython().run_line_magic('matplotlib', 'inline')


# In[10]:


init_notebook_mode(connected=True)

cf.go_offline()

tls.set_credentials_file(username='1784035059',api_key='D9f2RE1SMOZ52DqHWZ0a')


# In[11]:


conn = sql.connect('/Users/vincent/Documents/light_sensor.db')


# In[12]:


df = pd.read_sql_query("SELECT ID,light_value FROM light_sensor",conn)
conn.close()
df


# In[13]:


cf.go_offline()

tls.set_credentials_file(username='1784035059',api_key='D9f2RE1SMOZ52DqHWZ0a')
layout=dict(title='mobile phone light value',xaxis=dict(title='Time(hour)'),yaxis=dict(title='Light value'))
df.iplot(filename='Mobile phone light sensor value',layout=layout,title='mobile phone light value',kind='bar',x='ID', y='light_value')


# In[ ]:




